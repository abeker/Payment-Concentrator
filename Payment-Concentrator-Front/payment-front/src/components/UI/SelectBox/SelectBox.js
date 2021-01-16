import { Select } from 'antd';
const { Option } = Select;

const SelectBox = (props) => {
    return (
        <Select
            showSearch
            style={{ width: 200 }}
            placeholder="Select a bank"
            optionFilterProp="children"
            onChange={props.onChange}
            onFocus={props.onFocus}
            onBlur={props.onBlur}
            onSearch={props.onSearch}
            filterOption={(input, option) =>
              option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
        >
          <Option value="unicredit">Unicredit Bank</Option>
          <Option value="raiffeisen">Raiffeisen Bank</Option>
        </Select>
    );
}

export default SelectBox;