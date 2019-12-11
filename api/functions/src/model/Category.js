const uuid = require("uuid");
const _ = require("lodash");

class Category {
  constructor(props) {
    this.id = props.id;
    this.name = props.name;
    this.originalImage = props.originalImage;
    this.croppedImage = props.croppedImage;
    this.originalImageId = props.originalImageId;
    this.croppedImageId = props.croppedImageId;
  }

  // search user: 58 x 58 px
  // profile user: 89 x 89 px

  static collection() {
    return "categories";
  }

  static mediaKeyPaths() {
    return ["categoryImageUrl"];
  }

  createBody() {
    return _.extend(
      {
        id: uuid.v1(),
        createdAt: new Date().toISOString()
      },
      _.omitBy(this, _.isUndefined)
    );
  }

  updateBody() {
    return _.pickBy(this, _.identity);
  }

  responseBody() {
    return _.pickBy(this, _.identity);
  }
}

exports.Category = Category;
