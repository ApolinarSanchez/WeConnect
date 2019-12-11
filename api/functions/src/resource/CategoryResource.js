const CategoryManager = require("../manager/CategoryManager");
const { w, r, print } = require("../util/Util");

class CategoryResource {
  constructor(services) {
    const { router } = services;
    const categoryManager = new CategoryManager();

    // create a category
    router.post(
      "/category/",
      r(async (req, res, next) => {
        const { name } = req.body;

        if (!name) throw new Error("name not in body");

        const categoryFound = await w(categoryManager.findCategoryByName(name));

        if (categoryFound.error !== null)
          return res.status(500).send({ error: categoryFound.error.message });

        if (categoryFound.result.length > 0)
          throw new Error("category already exists");

        const categoryCreation = await w(categoryManager.create(req.body));

        if (categoryCreation.error !== null)
          return res
            .status(500)
            .send({ error: categoryCreation.error.message });

        return res.status(201).json(categoryCreation.result);
      })
    );

    // list all categories
    router.get(
      "/category/",
      r(async (req, res, next) => {
        return categoryManager
          .list()
          .then(result => res.json(result))
          .catch(next);
      })
    );

    // user by id
    router.get(
      "/category/:categoryId",
      r(async (req, res, next) => {
        const { categoryId } = req.params;

        const { result, error } = await w(categoryManager.findById(categoryId));

        if (error !== null)
          return res.status(400).send({ error: error.message });

        return res.status(200).json(result);
      })
    );

    // update category
    router.put(
      "/category/",
      r(async (req, res, next) => {
        const { result, error } = await w(categoryManager.update(req.body));

        if (error !== null)
          return res.status(400).send({ error: error.message });

        return res.status(200).json(result);
      })
    );
  }
}

exports.CategoryResource = CategoryResource;
