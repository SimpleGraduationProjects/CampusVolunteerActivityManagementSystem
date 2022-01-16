import {
  publishAct,
  allActs,
  actInfo,
  actPeople,
  checkActs,
  finishEvent,
  checkActRemove,
  userLogin,
  searchActs,
  getVolunteersList,
  deleteUser,
  getUserInfo,
  lunbo,
  allVolunteers,
  getActCounts
} from '../apis'
export async function publishActSubmit ({ startTime, endTime, title, location, description, supplyName, eventScore, maxnum, tags }) {
  let res = await publishAct({ startTime, endTime, title, location, description, supplyName, eventScore, maxnum, tags })
  return res
}
export async function getAllActs ({ current, size }) {
  let res = await allActs({ current, size })
  return res
}
export async function getActInfo ({ eventId }) {
  let res = await actInfo({ eventId })
  return res
}
export async function getActPeople ({ eventId }) {
  let res = await actPeople({ eventId })
  return res
}
export async function checkAct ({ eventId, status }) {
  let res = await checkActs({ eventId, status })
  return res
}
export async function finishAct ({ eventId, files, eventSummary }) {
  let res = await finishEvent({ eventId, files, eventSummary })
  return res
}
export async function removePeople ({ eventId, stuId, check }) {
  let res = await checkActRemove({ eventId, stuId, check })
  return res
}
export async function login ({ stuId, password, role }) {
  let res = await userLogin({ stuId, password, role })
  return res
}
export async function searchAct ({ size, current, title }) {
  let res = await searchActs({ size, current, title })
  return res
}
export async function getVolunteers ({ current, size }) {
  let res = await getVolunteersList({ current, size })
  return res
}
export async function kickUser ({ stuId }) {
  let res = await deleteUser({ stuId })
  return res
}
export async function getUserName () {
  let res = await getUserInfo()
  return res
}
export async function getLunBo () {
  let res = await lunbo()
  return res
}
export async function getAllVolunteers () {
  let res = await allVolunteers()
  return res
}
export async function getActCount () {
  let res = await getActCounts()
  return res
}
