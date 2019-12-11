import { ProjectManager } from '../manager/ProjectManager';
const { w, r, print } = require("../util/Util");

class ProjectResource {
  constructor(services) {
    const { router } = services;
    const projectManager = new ProjectManager();


    // get a proj by id
    router.get(
      "/project/:projectId",
      r(async (req, res, next) => {
        const { projectId } = req.params();

        if (projectId === undefined) return res.status(400).send({ error: "projectId is undefined" });

        const { result, error } = await w(projectManager.findById(projectId));

        if (error !== null) return res.status(500).send({ error: error.message });

        return res.status(200).json(result.body);
      })
    );


    // get all projects for a specific category
    router.get(
      "/project/:categoryId/category",
      r(async (req, res, next) => {
        const { result, error } = await w(projectManager.getAllProjectsByCategoryId(req));

        if (error !== null) return res.status(500).send({ error: error.message });

        return res.status(200).json(result);
      })
    );


    // list, if you want pagination you have to pass the proj id in the body
    router.get(
      "/project/",
      r(async (req, res, next) => {
        return projectManager
          .list(req)
          .then(result => res.json(result))
          .catch(next);
      })
    );


    // Create
    router.post(
      "/project/",
      r(async (req, res, next) => {
        console.log("hii")
        const { title, categoryId } = req.body;
        if (!title || !categoryId) return res.status(400).send({ error: "title or categoryId is undefined" });

        const { result, error } = await w(projectManager.create(req.body));

        if (error !== null)
          return res.status(500).send({ error: error.message });

        print(result)

        print(result)

        return res.status(200).json(result);
      })
    );


    // updates a Project
    router.put(
      "/project/:projectId",
      r(async (req, res, next) => {
        const { projectId } = req.params;

        if (!projectId) return res.status(400).send({ error: "projectId is undefined" });

        req.body.id = projectId;

        const { result, error } = await w(projectManager.update(req.body));

        if (error !== null)
          return res.status(500).send({ error: error.message });

        return res.status(200).json(result);
      })
    );


    // * MARK * Figure out how to upload images
    // upload Images
    router.post(
      "/project/media",
      r(async (req, res) => {

        const { result, error } = await w(projectManager.attachMedia(req));

        if (error) return res.status(500).send({ error: error.message });

        return res.status(200).json(result);
      })
    );
  }
}

exports.ProjectResource = ProjectResource;
