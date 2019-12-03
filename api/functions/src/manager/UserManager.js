const User = require('../model/User')
const request = require('request')
const util = require('util')
const { Admin, firestore } = require('../firebase/Admin')

const {
    mapArray,
    mapObject
} = require('../util/Util');

class UserManager {
    constructor() {
    }

    // lists all the users in the collection
    static async list() {
        return firestore
            .collection(User.collection()).get()
            .then(mapArray)
    }

    // Authenticate
    static async authenticate(body) {
        const { email, password } = body

        // Get key for api url
        const apiUrl = 'https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyAu9hWGVB1j8jYjmioEXi2lkMGDo-pnviY'

        const jsonObj = {
            'email': email,
            'password': password,
            'returnSecureToken': true,
        }

        return await util.promisify(request)({
            json: jsonObj,
            method: 'POST',
            uri: apiUrl,
        })
    }

    static async refresh(body) {
        const { refresh_token } = body
        const apiUrl = 'https://securetoken.googleapis.com/v1/token?key=AIzaSyAu9hWGVB1j8jYjmioEXi2lkMGDo-pnviY'

        const jsonObj = {
            'grant_type': 'refresh_token',
            'refresh_token': refresh_token,
        }

        return util.promisify(request)({
            json: jsonObj,
            method: 'POST',
            uri: apiUrl,
        })
    }

    // creates a user with the given props
    static async create(body) {
        const user = new User(body)
        const createBody = user.createBody()

        const userRecord = await Admin.auth().createUser({
            email: body.email,
            emailVerified: false,
            password: body.password,
            disabled: false,
        })

        createBody.firebaseUserUID = userRecord.uid

        console.log(createBody)

        await firestore
            .collection(User.collection())
            .doc(createBody.userId)
            .set(createBody)

        return createBody
    }

    // updates a user with the given userId and props
    static async update(body) {
        const user = new User(body)
        const _ = await firestore.collection(User.collection()).doc(user.userId).update(user.updateBody())
        return firestore.collection(User.collection()).doc(user.userId).get().then(mapObject)
    }

    // get user by id
    static async findById(userId) {
        return firestore.collection(User.collection()).doc(userId).get().then(mapObject)
    }

    static async queryUserById(object) {
        return this.findById(object.userId)
            .then((user) => {
                object.user = user
                return object
            })
    }

    // get users by email
    static async findUsersByEmail(email) {
        return firestore
            .collection(User.collection())
            .where('email', '==', email)
            .get()
            .then(mapArray)
    }
}

module.exports = UserManager;