const uuidv1 = require('uuid/v1');
const _ = require('lodash');

class User {
  constructor(props) {
    this.userId = props.userId;
    this.email = props.email;
    this.firstName = props.firstName;
    this.lastName = props.lastName;
    this.userName = props.userName;
    this.originalImage = props.originalImage;
    this.croppedImage = props.croppedImage;
    this.originalImageId = props.originalImageId;
    this.croppedImageId = props.croppedImageId;
    this.profileImageUrl = props.profileImageUrl;
  }

  // search user: 58 x 58 px
  // profile user: 89 x 89 px

  static collection() {
    return "users"
  }

  static mediaKeyPaths() {
    return [
      "profileImageUrl"
    ]
  }

  createBody() {
    return _.extend({ 
      userId: uuidv1(),
      createdAt: new Date().toISOString()
    }, _.omit(this, _.isUndefined))
  }

  updateBody() {
    return _.pickBy(this, _.identity)
  }

  responseBody() {
    return _.pickBy(this, _.identity)
  }
}

module.exports = User;