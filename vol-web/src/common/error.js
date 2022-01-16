export class ApiError extends Error {
  constructor (message, code) {
    super(message)
    this.msg = message
    this.code = code
  }
}
