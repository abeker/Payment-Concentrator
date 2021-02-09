import FormItem from '../FormItem/FormItem';
import { Button, Form, Input, InputNumber } from 'antd';
import classes from './NewBookForm.module.css';
import MultipleSelection from '../MultipleSelection/MultipleSelection';
const { TextArea } = Input;

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

const NewBookForm = (props) => {
  const [form] = Form.useForm();

  const options = props.genres.map(genre => {
      console.log(genre.name);
    return { 
        value: genre.name.toLowerCase(),
        label: genre.name,
        key: genre.id
    }
  });

  return (
    <Form
      {...formItemLayout}
      form={form}
      name="register"
      onFinish={props.onSubmit}
      scrollToFirstError
    >
    <FormItem 
        name="title"
        label="Title"
        required>
        <Input className={ classes.Input } />
    </FormItem>
    <FormItem 
        name="synopsis"
        label="Synopsis"
        required>
        <TextArea rows={4} className={ classes.Input } />
    </FormItem>
    <FormItem 
        name="genreIds"
        label="Genres">
        <MultipleSelection 
            options={ options }
            handleChange={ props.handleGenres } />
    </FormItem>
    <FormItem 
        name="numberOfPages"
        label="Number of Pages"
        required>
        <InputNumber min={0} />
    </FormItem>
    <FormItem 
        name="price"
        label="Recommended Price"
        required>
        <InputNumber min={0} />
    </FormItem>
    
      <Form.Item {...tailFormItemLayout}>
        <Button type="primary" htmlType="submit">
          Create
        </Button>
      </Form.Item>
    </Form>
    );
}

export default NewBookForm;