const admin = require('firebase-admin');
const functions = require('firebase-functions')
const serviceAccount = require('../../credentials/we-connect-stage-firebase-adminsdk-45pst-682de1659b');

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: 'https://we-connect-stage.firebaseio.com/',
    functions: functions,
    storageBucket: 'we-connect-stage.appspot.com',
});

exports.Admin = admin;
exports.firestore = admin.firestore();
exports.Firestore = admin.firestore();
exports.Functions = functions;