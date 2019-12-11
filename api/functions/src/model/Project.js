const uuid = require("uuid");
const _ = require("lodash");

class Project {
  constructor(props) {
    this.id = props.id;
    this.title = props.title;
    this.description = props.description;
    this.categoryId = props.categoryId;
    this.visibility = props.visibility;
    this.isActive = props.isActive;
    this.updatedAt = props.updatedAt;
    this.originalImage = props.originalImage;
    this.projectImageUrl = props.projectImageUrl;
    this.croppedImage = props.croppedImage;
    this.originalImageId = props.originalImageId;
    this.croppedImageId = props.croppedImageId;
  }

  // search user: 58 x 58 px
  // profile user: 89 x 89 px

  static collection() {
    return "projects";
  }

  static mediaKeyPaths() {
    return [
      'projectImageUrl',
    ]
  }

  createBody() {
    return _.extend(
      {
        id: uuid.v1(),
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString()
      },
      this.updateBody());
  }

  updateBody() {
    this.updatedAt = new Date().toISOString();
    return _.omitBy(this, _.isUndefined)
  }

  responseBody() {
    return _.pickBy(this, (value, key) => {
      switch (key) {
        case 'isActive': return false;
        default:
          if (Array.isArray(value)) return !_.isEmpty(value);
          return _.identity(value)
      }
    })
  }
}

exports.Project = Project;
