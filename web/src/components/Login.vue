<template>
  <a-layout>
    <a-layout-header></a-layout-header>
    <a-layout-content>
      <a-row>
        <a-col :span="8"></a-col>
        <a-col :span="8">
          <a-form
            id="components-form-demo-normal-login"
            :form="form"
            class="login-form"
            @submit="handleSubmit"
          >
            <a-form-item>
              <a-input
                v-decorator="[
                  'id',
                  { rules: [{ required: true, message: 'Please input your id!' }] }
                ]"
                placeholder="id"
              >
                <a-icon
                  slot="prefix"
                  type="user"
                  style="color: rgba(0,0,0,.25)"
                />
              </a-input>
            </a-form-item>
            <a-form-item>
              <a-input
                v-decorator="[
                  'password',
                  { rules: [{ required: true, message: 'Please input your Password!' }] }
                ]"
                type="password"
                placeholder="Password"
              >
                <a-icon
                  slot="prefix"
                  type="lock"
                  style="color: rgba(0,0,0,.25)"
                />
              </a-input>
            </a-form-item>
            <a-form-item>
              <a-checkbox
                v-decorator="[
                'remember',
                {
                  valuePropName: 'checked',
                  initialValue: true,
                }
              ]"
              >
                Remember me
              </a-checkbox>
              <a
                class="login-form-forgot"
                href=""
              >
                Forgot password
              </a>
              <a-button
                type="primary"
                html-type="submit"
                class="login-form-button"
              >
                Log in
              </a-button>
                Or <a href="">
                register now!
              </a>
            </a-form-item>
          </a-form>
        </a-col>
        <a-col :span="8"></a-col>
      </a-row>
    </a-layout-content>
    <a-layout-footer></a-layout-footer>
  </a-layout>
</template>

<script>
  import {message} from 'ant-design-vue';

  export default {
    name: "Login",
    data() {
      return {

      }
    },
    beforeCreate () {
      this.form = this.$form.createForm(this);
    },
    methods: {
      handleSubmit (e) {
        e.preventDefault();
        this.form.validateFields((err, values) => {
          if (!err) {
            message.error(err);
          }

          this.$api.login(values)
            .then((response) => {
              this.$router.push("/User");
            })
            .catch((error) => {
              message.error(error);
            });
        });
      }
    }
  }
</script>

<style scoped>
  #components-form-demo-normal-login .login-form {
    max-width: 300px;
  }
  #components-form-demo-normal-login .login-form-forgot {
    float: right;
  }
  #components-form-demo-normal-login .login-form-button {
    width: 100%;
  }
</style>
