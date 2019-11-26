const admin = require('firebase-admin')
const serviceAccount = require('../../credentials/we-connect-stage-firebase-adminsdk-s2fsa-f889c19e71')

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: 'https://we-connect-stage.firebaseio.com/',
    storageBucket: 'we-connect-stage.appspot.com',
})

exports.Admin = admin
exports.firestore = admin.firestore()