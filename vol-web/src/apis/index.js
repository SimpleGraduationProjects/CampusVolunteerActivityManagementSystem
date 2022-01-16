import { Api } from '../common'

export function publishAct (params) {
  return Api.call('api/addEvent', params)
}
export function allActs (params) {
  return Api.call('api/qryEventForPage/admin', params)
}
export function actInfo (params) {
  return Api.call('api/qryEventInfo', params)
}
export function actPeople (params) {
  return Api.call('api/qryUserJoinEvent', params)
}
export function checkActs (params) {
  return Api.call('api/updateEventStatus', params)
}
export function finishEvent (params) {
  return Api.call('api/finishEvent', params)
}
export function checkActRemove (params) {
  return Api.call('api/checkEventApply', params)
}
export function userLogin (params) {
  return Api.call('api/user/login', params)
}
export function searchActs (params) {
  return Api.call('api/qryEventByTitleForPage', params)
}
export function getVolunteersList (params) {
  return Api.call('api/user/askUserOnline', params)
}
export function deleteUser (params) {
  return Api.call('api/user/kickUser', params)
}
export function getUserInfo (params) {
  return Api.call('api/user/qryUser', params)
}
export function lunbo (params) {
  return Api.call('api/picture/getPictureTopSize/3', params)
}
export function allVolunteers (params) {
  return Api.call('api/user/qryAllUser', params)
}
export function getActCounts (params) {
  return Api.call('api/web/getIndexData', params)
}
