const { Functions } = require("./dist/firebase/Admin");

const { App } = require('./dist/index');


/*n
 * Since our entry point is within here using `npm start` or `node index.js`
 * we can go ahead and initialize our App and start it.
 */
const router = new App().start();

exports.resource = Functions.https.onRequest((req, res, next) => { router(req, res, next) })
