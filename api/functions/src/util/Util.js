const util = require('util');
const fs = require('fs');
const _ = require('lodash');
const mime = require('mime-types');
const uuidv1 = require('uuid').v1
const Busboy = require('busboy');
const Image = require('../model/Image')


exports.print = (value) => {
    console.log(util.inspect(value, false, null, true ))
};

// maps the firebase result to an array if applicable
exports.mapArray = (result) => {
    const docs = result.docs || result
    const values = docs.map((doc) => doc.data())
    if (values.length === 0) {
        return []
    }
    return values
}

// mapes the firebase result to a single object
exports.mapObject = (result) => {
    return result.data()
}

// Few wrappers for handling and unwrapping promises safely
exports.r = (fn) => (req, res, next) => {
    Promise.resolve(fn(req, res, next)).catch(next)
};

exports.w = (promise) => (
    promise.then((result) => ({ result, error: null })).catch((error) => {
        console.log(error.stack);
        return { error, result: null }
    })
);

exports.attachMedia = async (req, bucket, mediaKeyMap, folderpath, metadata) => {
    const data = await parseMultipartForm(req);
    return await uploadToBucket(data, bucket, mediaKeyMap, folderpath, metadata)
      .then((i) => i.map((v) => new Image(v)))
}

const uploadToBucket = async (data, bucket, mediaKeyPath, folderpath, metadata) => {
    const {
        filenames,
        filepaths,
        fields,
    } = data

    if (filenames === undefined ||
      filepaths === undefined ||
      bucket === undefined ||
      fields === undefined
    ) throw Error('Params must be defined')

    // parse file keys
    const keys = Object.keys(filenames)

    // create promises for each file
    const promises = keys.map((imageId) => {
        const _folderpath = () => {
            if (!_.identity(folderpath)) return ''
            return `${folderpath}/`
        }
        const destination = `${_folderpath()}${filenames[imageId]}`
        const filepath = filepaths[imageId]
        const mimeType = mime.lookup(filepath)
        const _metadata = _.extend({
            'mediaKeyPath': mediaKeyPath,
            // download token
            'firebaseStorageDownloadTokens': uuidv1(),
        }, metadata)
        return bucket.upload(
          filepath,
          {
              'destination': destination,
              'metadata': {
                  'contentType': mimeType,
                  'metadata': _metadata,
              },
          },
        )
    })

    const results = await Promise.all(promises)

    // end file writes
    keys.forEach((i) => fs.unlinkSync(filepaths[i]))

    return results.map((i) => i[1])
}

const parseMultipartForm = (req) => {
    return new Promise((resolve, reject) => {
        const busboy = new Busboy({
            headers: req.headers,
        })

        // This object will accumulate all the fields, keyed by their name
        const fields = {}

        // This object will accumulate all the uploaded files, keyed by their name.
        const filenames = {}
        const filepaths = {}

        // This code will process each non-file field in the form.
        busboy.on('field', (fieldname, val) => {
            fields[fieldname] = val
        })

        // This code will process each file uploaded.
        busboy.on('file', (fieldname, file, filename) => {
            const filepath = () => {
                // for some weird reason, intellij http can't resolve the actual path name
                // of the given file
                // if (req.headers.platform === "http") {
                //   const Path = require("path");
                //   const resolved = Path.resolve(filename)
                //   return resolved.substring(0, resolved.lastIndexOf("/")) + `/http/${filename}`
                // }
                return filename
            }

            // Note: os.tmpdir() points to an in-memory file system on GCF
            // Thus, any files in it must fit in the instance's memory.
            filenames[fieldname] = filename
            filepaths[fieldname] = filepath()

            const writeStream = fs.createWriteStream(filepath())
            file.pipe(writeStream)
        })

        // Triggered once all uploaded files are processed by Busboy.
        // We still need to wait for the disk writes (saves) to complete.
        busboy.on('finish', () => {
            const result = {
                'filenames': filenames,
                'filepaths': filepaths,
                'fields': fields,
            }
            // if (!_.isEmpty(fields)) console.log(fields)
            // if (!_.isEmpty(filepaths)) console.log(filepaths)
            // if (!_.isEmpty(filenames)) console.log(filenames)
            resolve(result)
        })
          .on('error', (error) => {
              reject(error)
          })

        if (req.rawBody) {
            busboy.end(req.rawBody)
        } else {
            req.pipe(busboy)
        }
    })
}

exports.makeExpressApp = () => {
    const cors = require('cors')
    const express = require('express')

    // eslint-disable-next-line new-cap
    const router = express.Router()
    router.use(cors({ origin: true }))

    const app = express()
    const bodyParser = require('body-parser')
    app.use(bodyParser.urlencoded({ extended: false }))
    app.use(bodyParser.json())

    // This is the middleware function which will be called before any routes get
    // hit which are defined after this point, i.e. in your app.js
    app.use((req, res, next) => {
        // require authorization for POST methods
        // then attach the user id into the request body
        // TODO: remove this once auth is confirmed
        // if (req.method === 'POST') {
        //     const userId = req.headers.user_id
        //     if (!userId) return res.status(403).send({ error: 'unauthorized! user_id is undefined in headers.' })
        //     req.body.userId = userId
        // }

        // console.log(req.headers)
        if (req.body !== undefined) {
            console.log(req.body)
        }

        // Carry on with the request chain
        next()

        return true
    })

    app.use(router)

    return { app, router }
};
