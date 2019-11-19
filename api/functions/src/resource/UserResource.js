const User = require('../model/User')
const { w, r } = require('../util/Util')
const UserManager = require('../manager/UserManager')

class UserResource {
  constructor(services) {
    const router = services.router

    // list all users
    router.get('/user/', (req, res, next) =>  {
      return UserManager.list()
      .then(result => res.json(result))
      .catch(next)
    })

    // create a user
    router.post('/user/', (req, res, next) => {
      const { email } = req.body
      if (!email) { throw Error('email not in body') }
      return UserManager.getUserByEmail(req.body)
      .then(result => {
        if (result.length > 0) { throw Error('user already exists') }
        return UserManager.create(req.body)
      })
      .then(result => res.json(result))
      .catch(next)
    })

    // updates a user
    router.put('/user/:userId', r(async (req, res, next) =>  {
      const { result, error } = await w(UserManager.update(req.body))

      if (error !== null) return res.status(500).send({ error: error.message })

      return res.status(200).json(result)
    }))

    // user by id
    router.get('/user/:userId', r(async (req, res, next) =>  {
      const { result, error } = await w(UserManager.getById(req.params.userId))

      if (error !== null) return res.status(500).send({ error: error.message })

      return res.status(200).json(result)
    }))
  }
}

module.exports = UserResource