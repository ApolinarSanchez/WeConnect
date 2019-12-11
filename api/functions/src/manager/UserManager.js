import { User } from "../model/User";
const request = require("request");
const util = require("util");
const { Admin, firestore } = require("../firebase/Admin");

const { mapArray, mapObject, attachMedia } = require("../util/Util");

class UserManager {
  constructor() {}
  // lists all the users in the collection
  async list() {
    return firestore
      .collection(User.collection())
      .get()
      .then(mapArray);
  }

  // Authenticate
  async authenticate(body) {
    const { email, password } = body;

    // Get key for api url
    const apiUrl =
      "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyAu9hWGVB1j8jYjmioEXi2lkMGDo-pnviY";

    const jsonObj = {
      email: email,
      password: password,
      returnSecureToken: true
    };

    return await util.promisify(request)({
      json: jsonObj,
      method: "POST",
      uri: apiUrl
    });
  }

  async refresh(body) {
    const { refresh_token } = body;
    const apiUrl =
      "https://securetoken.googleapis.com/v1/token?key=AIzaSyAu9hWGVB1j8jYjmioEXi2lkMGDo-pnviY";

    const jsonObj = {
      grant_type: "refresh_token",
      refresh_token: refresh_token
    };

    return util.promisify(request)({
      json: jsonObj,
      method: "POST",
      uri: apiUrl
    });
  }

  // creates a user with the given props
  async create(body) {
    const user = new User(body);
    const createBody = user.createBody();

    const userRecord = await Admin.auth().createUser({
      email: body.email,
      emailVerified: false,
      password: body.password,
      disabled: false
    });

    createBody.firebaseUserUID = userRecord.uid;

    console.log(createBody);

    await firestore
      .collection(User.collection())
      .doc(createBody.userId)
      .set(createBody);

    return createBody;
  }

  // updates a user with the given userId and props
  async update(body) {
    const user = new User(body);
    const _ = await firestore
      .collection(User.collection())
      .doc(user.userId)
      .update(user.updateBody());
    return firestore
      .collection(User.collection())
      .doc(user.userId)
      .get()
      .then(mapObject);
  }

  // get user by id
  async findById(userId) {
    return firestore
      .collection(User.collection())
      .doc(userId)
      .get()
      .then(mapObject);
  }

  // get users by email
  async findUsersByEmail(email) {
    return firestore
      .collection(User.collection())
      .where("email", "==", email)
      .get()
      .then(mapArray);
  }

  async attachMedia(req) {
    const { mediaKeyPath, userId } = req.query
    // const { userId } = req.body

    if (userId === undefined) throw Error('userId is undefined')
    if (mediaKeyPath === undefined) throw Error('mediaKeyPath is undefined')

    // grab the associated story
    const user = await this.findById(userId)
    if (user === undefined) throw Error('user does not exist')

    // validate key maps
    if (!User.mediaKeyPaths().includes(mediaKeyPath)) throw Error('Invalid mediaKeyPath in User')

    // upload
    const metadata = {
      'userId': userId,
    }
    const media = await attachMedia(
      req,
      Admin.storage().bucket(),
      mediaKeyPath,
      `users/${userId}`,
      metadata,
    )

    // add the download link to the respective key map
    media.forEach((i) => {
      user[i.mediaKeyPath] = i.downloadLink()
    })

    return await this.update(user)
      .then((i) => new User(i).responseBody())
  }
}

module.exports = UserManager;
