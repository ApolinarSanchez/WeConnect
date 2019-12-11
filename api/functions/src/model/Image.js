

class Image {
  constructor(props) {
    this.imageId = props.id

    this.name = props.name

    this.createdAt = props.timeCreated
    this.updatedAt = props.updated

    this.contentType = props.contentType
    this.size = props.size
    this.bucket = props.bucket

    this.filename = props.metadata.filename
    this.mediaLink = props.mediaLink
    this.mediaKeyPath = props.metadata.mediaKeyPath

    this.firebaseStorageDownloadTokens = props.metadata.firebaseStorageDownloadTokens
  }

  static collection() {
    return 'images'
  }

  downloadLink() {
    const link = 'https://firebasestorage.googleapis.com/v0/b/' +
      this.bucket + '/o/' + encodeURIComponent(this.name) +
      '?alt=media&token=' + this.firebaseStorageDownloadTokens
    return link
  }
}

module.exports = Image
