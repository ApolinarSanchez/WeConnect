const UserManager = require('../manager/UserManager')
const { w, r } = require('../util/Util')

class UserResource {
  constructor(services) {
    const router = services.router

    // list all users
    router.get('/user/', r (async (req, res, next) => {
      return UserManager.list()
          .then((result) => res.json(result))
          .catch(next)
    }))

    // sign in user
    router.post('/user/signIn', r(async (req, res, next) => {
      const { result, error } = await w(UserManager.authenticate(req.body))

      if (error !== null) return res.status(500).send({ error: error.message })

      return res.status(200).json(result.body)
    }))

    // authenticate by exhanging refresh for an id token
    router.post('/user/refresh', r(async (req, res, next) => {
      const { refreshToken } = req.body
      if (!refreshToken) {
        throw Error('refresh_token not in body')
      }

      const { result, error } = await w(UserManager.refresh(req.body))

      if (error !== null) return res.status(500).send({ error: error.message })

      return res.status(200).json(result.body)
    }))

    // create a user
    router.post('/user/', r(async (req, res, next) => {
      const { email, password } = req.body
      if (!email) {
        throw Error('email not in body')
      } else if (!password) {
        throw Error('password not in body')
      }
      const usersFound = await w(UserManager.findUsersByEmail(email))

      if (usersFound.error !== null) return res.status(500).send({ error: usersFound.error.message })

      if (usersFound.result.length > 0) {
        throw Error('user already exists')
      }

      delete req.body.userId

      const userCreation = await w(UserManager.create(req.body))

      if (userCreation.error !== null) return res.status(500).send({ error: userCreation.error.message })

      const userAuth = await w(UserManager.authenticate({ email: email, password: password }))

      if (userAuth.error !== null) return res.status(409).send({ error: userAuth.error.message })

      return res.json(userAuth.result.body)
    }))

    // updates a user
    router.put('/user/:userId', r(async (req, res, next) => {
      const { result, error } = await w(UserManager.update(req.body))

      if (error !== null) return res.status(500).send({ error: error.message })

      return res.status(200).json(result)
    }))

    // user by id
    router.get('/user/:userId', r(async (req, res, next) => {
      const { result, error } = await w(UserManager.getById(req.params.userId))

      if (error !== null) return res.status(500).send({ error: error.message })

      return res.status(200).json(result)
    }))

    // upload Images
    router.post('/user/media', r(async (req, res) => {
      const { result, error } = await w(UserManager.attachMedia(req))
      if (error) return res.status(500).send({ error: error.message })
      return res.status(200).json(result)
    }))
  }
}

module.exports = UserResource
