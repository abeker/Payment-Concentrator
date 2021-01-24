import { Button, Form, Input } from 'antd';
import FormItem from '../FormItem/FormItem';

const layout = {
  labelCol: {
    span: 8,
  },
  wrapperCol: {
    span: 16,
  },
};
const tailLayout = {
  wrapperCol: {
    offset: 8,
    span: 16,
  },
};

const LoginForm = (props) => {
  return (
    <Form
      {...layout}
      name="basic"
      initialValues={{
        remember: true,
      }}
      onFinish={props.onFinish}
      onFinishFailed={props.onFinishFailed}>

        <FormItem 
            label="Username"
            name="username"
            minLength="5"
            required>
            <Input />
        </FormItem>

        <FormItem 
            label="Password"
            name="password"
            minLength="5"
            required>
            <Input.Password />
        </FormItem>

        <Form.Item {...tailLayout}>
            <Button type="primary" htmlType="submit">
              Submit
            </Button>
        </Form.Item>
    </Form>
  );
};

export default LoginForm;