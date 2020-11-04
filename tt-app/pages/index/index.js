const SERVER = 'https://localhost:40443'
const API = `${SERVER}/toutiao_demo/`
const header = {'content-type': "application/x-www-form-urlencoded"}
var code, anonymous_code,openid,session_key,unionid,anonymous_openid,access_token;

Page({
  tt_login_tap() {
    tt.login({
      success(res) {
        console.log('tt.login', 'success', res)
        code = res.code
        anonymous_code = res.anonymousCode
      }, fail(res) {
        console.log('tt.login', 'fail', res)
      }
    })
  },
  code2Session1_tap() {
    const url = `${API}code2Session1`
    console.log(url)
    tt.request({
      url,
      data: {
        code
      },
      success(res) {
        console.log('code2Session(code)', 'success', res)
        openid = res.data.openid
        session_key = res.data.session_key
        unionid = res.data.unionid
      }, fail(res) {
        console.log('code2Session(code)', 'fail', res)
      }
    });
  },
  code2Session2_tap() {
    const url = `${API}code2Session2`
    console.log(url)
    tt.request({
      url,
      data: {
        anonymous_code
      },
      success(res) {
        console.log('code2Session(anonymousCode)', 'success', res)
        anonymous_openid = res.data.anonymous_openid
      }, fail(res) {
        console.log('code2Session(anonymousCode)', 'fail', res)
      }
    });
  },
  getAccessToken_tap() {
    const url = `${API}getAccessToken`
    console.log(url)
    tt.request({
      url,
      success(res) {
        console.log('getAccessToken', 'success', res)
        access_token = res.data.access_token
      }, fail(res) {
        console.log('getAccessToken', 'fail', res)
      }
    });
  },
   setUserStorage_tap() {
    const url = `${API}setUserStorage`
    const method = "POST";
    console.log(url)
   tt.request({
      url,
      data:{
        session_key,
        access_token,
        openid
      },
      success(res) {
        console.log('setUserStorage', 'success', res)
      }, fail(res) {
        console.log('setUserStorage', 'fail', res)
      }
    });
  },
   removeUserStorage_tap() {
    const url = `${API}removeUserStorage`
    const method = "POST";
    console.log(url)
    const key =JSON.stringify( ["key1"])
    tt.request({
      url,
      data:{
        session_key,
        access_token,
        openid,
        key,
      },
      success(res) {
        console.log('removeUserStorage', 'success', res)
      }, fail(res) {
        console.log('removeUserStorage', 'fail', res)
      }
    });
  },
   createQRCode_tap() {
     const that = this
    const url = `${API}createQRCode`
    const method = "POST";
    console.log(url)
    tt.request({
      url,
      data:{
        access_token
      },
      success(res) {
        console.log('createQRCode', 'success', res)
     const qrcode = res.data;
that.setData({qrcode})
      }, fail(res) {
        console.log('createQRCode', 'fail', res)
      }
    });
  },
})
