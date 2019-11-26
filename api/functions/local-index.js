require('dotenv').config();

const { print } = require("./src/util/Util");

// MARK: Local Setup

function devLocal() {
    const { app, router } = makeExpressApp();
    const services = { router };

    // MARK: Resources
    new (require('./src/resource/UserResource'))(services);
    new (require('./src/resource/ProjectResource'))(services);
    new (require('./src/resource/MessageResource'))(services);
    new (require('./src/resource/TagResource'))(services);
    new (require('./src/resource/ConfigResource'))(services);
    new (require('./src/resource/CategoryResource'))(services);

    app.listen(3000, () => {
        console.log('Example app listening on port 3000.')
    })
}

// MARK: Environment Setup

devLocal();

function makeExpressApp() {
    const cors = require('cors');
    const express = require('express');

    const router = express.Router();
    router.use(cors({ origin: true }));

    const app = express();
    const bodyParser = require('body-parser');
    app.use(bodyParser.urlencoded({ extended: false }));
    app.use(bodyParser.json());

    //This is the middleware function which will be called before any routes get
    // hit which are defined after this point, i.e. in your index.js
    app.use( (req, res, next) => {

        // require authorization for POST methods
        // then attach the user id into the request body
        if (req.method === 'POST') {
            const userId = req.headers.user_id;
            if (!userId) return res.status(403).send({ error: 'unauthorized!' });
            req.body.userId = userId
        }

        print(req.method + " " + req.url);
        if (req.body !== undefined) {
            print(req.body)
        }

        //Carry on with the request chain
        next();

        return true
    });

    app.use(router);

    return { app, router }
}