layui.define(['layer', 'jquery'], function (exports) {
  var $ = layui.jquery

  var common = {
    //接口地址
    api: 'http://localhost:8080',

    ajax: function (url, data, ft, method, errorft, async) {
      var roleSaveLoading = top.layer.load(2)
      $.ajax({
        url: url,
        data: data,
        type: method == undefined ? 'get' : method,
        async: async == undefined ? true : async,
        contentType: 'application/json; charset=UTF-8',
        timeout: 5000,
        dataType: 'json',
        beforeSend: function (request) {
          request.setRequestHeader('authorization', common.getToken('token'))
        },
        success: function (res) {
          top.layer.close(roleSaveLoading)
          if (res.code != 200 && res.code != undefined) {
            if (res.code == 5000) {
              layer.msg('请登录', { icon: 2, time: 2000 }, function () {
                window.location.href = '/login.html'
              })
            } else if (res.code == 5001) {
              layer.msg('用户没有权限操作', { icon: 2, time: 1000 })
            }
            if (errorft != null && errorft != undefined) {
              errorft(res)
            }
          } else {
            if (ft != null && ft != undefined) {
              ft(res)
            }
          }
        },
        error: function () {
          top.layer.close(roleSaveLoading)
          layer.msg('服务器好像除了点问题！请稍后试试')
          // window.location = '/login.html'
        },
      })
    },

    login: function (res) {
      if (res.code == 5000) {
        layer.msg('请登录', { icon: 2, time: 1000 }, function () {
          window.location.href = '/login.html'
        })
      } else if (res.code == 5001) {
        layer.msg('用户没有权限操作', { icon: 2, time: 1000 })
      } else {
        layer.msg('请登录', { icon: 2, time: 1000 }, function () {
          window.location.href = '/login.html'
        })
      }
    },
    //设置token
    setToken: function (value) {
      return layui.data('token', {
        key: 'token',
        value: value,
      })
    },
    //获取token
    getToken: function () {
      return layui.data('token').token
    },
    compare: function (data) {
      var compare = function (obj1, obj2) {
        var val1 = obj1.id
        var val2 = obj2.id
        if (val1 < val2) {
          return -1
        } else if (val1 > val2) {
          return 1
        } else {
          return 0
        }
      }
      return data.sort(compare)
    },
  }

  exports('common', common)
})
