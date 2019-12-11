import { Project } from "../model/Project";
import { Firestore, Admin } from "../firebase/Admin";

const { mapArray, mapObject, w, attachMedia, print } = require("../util/Util");

class ProjectManager {
  constructor() {}

  // lists all the projects in the collection
  async list(req) {
    const { projectId } = req.body;

    if (projectId) {
      const { result, error } = await w(this.findById(projectId));
      if (error !== null) throw new Error("Could not retrieve projects");

      return Firestore
        .collection(Project.collection())
        .orderBy("updatedAt")
        .startAfter(result)
        .limit(10)
        .get()
        .then(mapArray)
    }

    return Firestore.collection(Project.collection())
      .orderBy("updatedAt")
      .limit(10)
      .get()
      .then(mapArray);
  }

  async create(body) {
    const project = new Project(body).createBody();

    await Firestore.collection(Project.collection())
      .doc(project.id)
      .create(project);
    return project;
  }


  async update(body) {
    const collection = Firestore.collection(Project.collection())
    const object = new Project(body)
    const updateBody = object.updateBody()
    await collection.doc(updateBody.id).update(updateBody)
    return await this.findById(updateBody.id)
  }

  // find by project id
  async findById(projectId) {
    return await Firestore.collection(Project.collection())
      .doc(projectId)
      .get()
      .then(mapObject);
  }

  // find by category id
  async getAllProjectsByCategoryId(req) {

    const { categoryId } = req.params;
    const { projectId } = req.query;

    if (categoryId === undefined) throw new Error("categoryId is undefined");


    if (projectId)
      return await Firestore.collection(Project.collection())
        .where("categoryId", "==", categoryId)
        .orderBy("updatedAt")
        .startAfter(projectId)
        .limit(10)
        .get()
        .then(mapObject);

    return await Firestore.collection(Project.collection())
      .where("categoryId", "==", categoryId)
      .orderBy("updatedAt")
      .limit(10)
      .get()
      .then(mapArray);
  }


  async attachMedia(req) {
    const { projectId, mediaKeyPath, userId } = req.query;

    if (mediaKeyPath === undefined) throw Error('mediaKeyPath is undefined')

    // grab the associated story
    if (projectId === undefined) throw Error('projectId is undefined')
    const project = await this.findById(projectId);
    if (project === undefined) throw Error('story does not exist');

    print(project);

    // validate key maps
    if (!Project.mediaKeyPaths().includes(mediaKeyPath)) throw Error('Invalid mediaKeyPath in Project');

    // upload
    const metadata = {
      'projectId': projectId,
    }
    const bucket = Admin.storage().bucket();
    const media = await attachMedia(
      req,
      bucket,
      mediaKeyPath,
      `users/${userId}/project/${projectId}`,
      metadata,
    )

    // add the download link to the respective key map
    media.forEach((i) => {
      project[i.mediaKeyPath] = i.downloadLink()
    })

    return await this.update(project)
      .then((i) => new Project(i).responseBody())
  }
}

exports.ProjectManager = ProjectManager;