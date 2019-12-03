require('dotenv').config();

const functions = require('firebase-functions');

// MARK: Staging Setup

function devStage() {
    console.log('Booting staging server');

    const { app, router } = makeExpressApp();
    const services = { router };

    new (require('./src/resource/UserResource'))(services);
    new (require('./src/resource/ProjectResource'))(services);
    new (require('./src/resource/MessageResource'))(services);
    new (require('./src/resource/TagResource'))(services);
    new (require('./src/resource/CategoryResource'))(services);

    exports.resource = functions.https.onRequest((req, res) => { router(requestPreHook(req, res), res) })
}

// MARK: Environment Setup 

devStage();

// MARK: Utility

function requestPreHook(req, res) {
    if (!req.path) {
        req.path = '/';
        req.url = '/' + req.url
    }

    // require authorization for POST methods
    // then attach the user id into the request body
    if (req.method === 'POST') {
        const userId = req.get('user_id');
        if (!userId) return res.status(403).send({ error: 'unauthorized! user_id required in headers' });
        req.body.userId = userId
    }

    console.log(req.method + " " + req.url);
    if (req.body !== undefined && req.body !== {}) {
        console.log(req.body)
    }
    if (req.query !== undefined && req.query !== {}) {
        console.log(req.query)
    }

    return req
}

function makeExpressApp() {
    const express = require('express');

    const router = express.Router();
    router.use(require('cors')({ origin: true }));

    const app = express();
    const bodyParser = require('body-parser');
    app.use(bodyParser.urlencoded({ extended: false }));
    app.use(bodyParser.json());

    app.use(router);

    return { app, router }
}