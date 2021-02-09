import { Button, Form, Input, InputNumber } from 'antd';
import React from 'react';
import FormItem from '../FormItem/FormItem';
import SelectBox from '../SelectBox/SelectBox';
import classes from './RegistrationForm.module.css';

const formItemLayout = {
  labelCol: {
    xs: {
      span: 24,
    },
    sm: {
      span: 8,
    },
  },
  wrapperCol: {
    xs: {
      span: 24,
    },
    sm: {
      span: 16,
    },
  },
};
const tailFormItemLayout = {
  wrapperCol: {
    xs: {
      span: 24,
      offset: 0,
    },
    sm: {
      span: 16,
      offset: 8,
    },
  },
};

const RegistrationForm = (props) => {
  const [form] = Form.useForm();

  let selectBox = null;
  if(props.isBankSelected) {
      selectBox = <Form.Item 
              name="selectedGenres"
              label="Genres"
              rules={[{
                  required: true,
                  message: 'Please select one bank!'
              }]}>
            <SelectBox />
        </Form.Item>
  }

  return (
    <Form
      {...formItemLayout}
      form={form}
      name="register"
      onFinish={props.onSubmit}
      scrollToFirstError
    >
    <FormItem 
        name="fullname"
        label="Full Name"
        minLength="6"
        required>
        <Input className={ classes.Input } />
    </FormItem>
    <FormItem 
        name="country"
        label="Country"
        required>
        <Input className={ classes.Input } />
    </FormItem>
    <FormItem 
        name="city"
        label="City"
        required>
        <Input className={ classes.Input } />
    </FormItem>
    <FormItem 
        name="street"
        label="Street"
        required>
        <Input className={ classes.Input } />
    </FormItem>
    <FormItem 
        name="zipCode"
        label="Zip Code"
        minLength="5"
        maxLength="5"
        required>
        <Input className={ classes.Input } />
    </FormItem>
    <FormItem 
        name="membership"
        label="Membership Amount"
        required>
        <InputNumber min={0} defaultValue={0} />
    </FormItem>
    
    { selectBox }

      <Form.Item {...tailFormItemLayout}>
        <Button type="primary" htmlType="submit">
          Register
        </Button>
      </Form.Item>
    </Form>
  );
};

export default RegistrationForm;