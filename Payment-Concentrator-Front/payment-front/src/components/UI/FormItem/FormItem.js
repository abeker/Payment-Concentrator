import { Form } from 'antd';

const FormItem = (props) => {
    let ruleArray = [];
    if(props.required) {
        ruleArray.push({
            required: true,
            message: 'Please input your '+ props.label +'!'
        });
    } if(props.minLength) {
        ruleArray.push({
            min: Number(props.minLength),
            message: 'Must contain minimum '+ props.minLength +' characters.'
        });
    } if(props.maxLength) {
        ruleArray.push({
            max: Number(props.maxLength),
            message: 'Must contain maximum '+ props.maxLength +' characters.'
        });
    }

    return (
        <Form.Item
            name={props.name}
            label={props.label}
            rules={ruleArray}
          >
          {props.children}
      </Form.Item>
    );
}

export default FormItem;