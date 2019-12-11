import { CategoryResource } from "./resource/CategoryResource";
import { UserResource } from "./resource/UserResource";
import { ProjectResource} from "./resource/ProjectResource";
import { makeExpressApp } from "./util/Util";
import { Functions } from "./firebase/Admin"
import dotenv from "dotenv";
dotenv.config();

class App {
  constructor() {}

  start() {
    const { app, router } = makeExpressApp();
    const services = { router };

    // MARK: Resources

    new UserResource(services);
    new CategoryResource(services);
    new ProjectResource(services);

    // app.listen(8080, () => {
    //   console.log('Example deploy listening on port 8080')
    // })

    return router
  }
}

exports.App = App;


/*
 * Since our entry point is within here using `npm start` or `node index.js`
 * we can go ahead and initialize our App and start it.
 */
// const app = new App()
// app.start()
