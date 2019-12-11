import { Category } from "../model/Category";
import { Firestore } from "../firebase/Admin";

const { mapArray, mapObject } = require("../util/Util");

class CategoryManager {
  constructor() {}
  // lists all the users in the collection
  async list() {
    return Firestore.collection(Category.collection())
      .get()
      .then(mapArray);
  }
  async create(body) {
    const category = new Category(body).createBody();
    console.log(category);
    await Firestore.collection(Category.collection())
      .doc(category.id)
      .create(category);
    return category;
  }

  // updates a category with the given categoryId and props
  async update(body) {
    const category = new Category(body);
    return await Firestore.collection(Category.collection())
      .doc(category.categoryId)
      .update(category.updateBody());
  }

  // get category by id
  async findById(categoryId) {
    return await Firestore.collection(Category.collection())
      .doc(categoryId)
      .get()
      .then(mapObject);
  }

  // get category by name
  async findCategoryByName(name) {
    return Firestore.collection(Category.collection())
      .where("name", "==", name)
      .get()
      .then(mapArray);
  }
}

module.exports = CategoryManager;
