const User = require('../model/User');
const {
    mapArray,
    mapObject
} = require('../util/Util');

class UserManager {
    constructor(services) {
        services.manager.UserManager = this;
        this.services = services;
        this.firestore = services.firestore
    }

    // lists all the users in the collection
    static list() {
        return this.firestore
            .collection(User.collection()).get()
            .then(mapArray)
    }

    // creates a user with the given props
    static create(body) {
        const user = new User(body)
        const createBody = user.createBody()
        return this.firestore
            .collection(User.collection())
            .doc(createBody.userId)
            .set(createBody)
            .then(_ => createBody)
            .catch(error => error)
    }

    // updates a user with the given userId and props
    static async update(body) {
        const user = new User(body)
        const _ = await this.firestore.collection(User.collection()).doc(user.userId).update(user.updateBody());
        return await this.firestore.collection(User.collection()).doc(user.userId).get().then(mapObject)
    }

    // get user by id
    static async findById(userId) {
        return await this.firestore.collection(User.collection()).doc(userId).get().then(mapObject)
    }

    static queryUserById(object) {
        return this.getById(object.userId)
            .then(user => {
                object.user = user;
                return query
            })
    }
}

module.exports = UserManager;