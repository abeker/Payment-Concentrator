import { Select } from 'antd';
const { Option } = Select;

const MultipleSelection = (props) => {
    const options = props.options.map(option => {
        return <Option key={option.key} value={option.id} label={option.label}>
            <div className="demo-option-label-item">
              {option.label}
            </div>
          </Option>;
    });

    return (
        <Select
            style={{ width: "60%" }}
            mode="multiple"
            placeholder="Select..."
            onChange={ props.handleChange }
            optionLabelProp="label">
            { options }
        </Select>
    );
}

export default MultipleSelection;