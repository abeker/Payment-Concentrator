import { List, Avatar } from 'antd';
import classes from './PasswordAdvices.module.css';

const data = [
  {
    title: 'Make Your Password Long',
    description: 'The longer and more complex your password is, the longer is process of hacking. Recommended minimum password length is 7 characters.',
    image: 'https://www.flaticon.com/svg/vstatic/svg/2095/2095572.svg?token=exp=1612193655~hmac=bf25b6f450cff75b77bce3c26c05f996'
  },
  {
    title: 'Make Your Password Nonsense Phrase',
    description: 'Long passwords are good. Long passwords that include random words and phrases are better. Do not use dictionary words. A common phrase, "outofthepark," is only marginally more secure than a dictionary word. Also do not use characters that are sequential on a keyboard such as numbers in order or the widely used “qwerty”.',
    image: 'https://www.flaticon.com/svg/vstatic/svg/73/73788.svg?token=exp=1612194208~hmac=778f7bf57863ca3a96fd89849b60842c'
  },
  {
    title: 'Include Numbers, Symbols, Uppercase & Lowercase Letters',
    description: 'Randomly mix up symbols and numbers with letters. If your password is a phrase, consider capitalizing the first letter of each new word, which will be easier for you to remember.',
    image: 'https://www.flaticon.com/svg/vstatic/svg/67/67689.svg?token=exp=1612194756~hmac=95a01b9ecc71c8ab71669986b43269d5'
  },
  {
    title: 'Avoid Using Obvious Personal Information',
    description: 'If there is information about you that is easily discoverable - such as your birthday, address, city of birth, high school, pets’ names - do not include them in your password. These only make your password easier to guess.',
    image: 'https://www.flaticon.com/svg/vstatic/svg/994/994285.svg?token=exp=1612195319~hmac=437791f509d9438a4de06e03fbca357d'
  },
  {
    title: 'Do Not Reuse Passwords',
    description: 'If your account is compromised and you use this email address and password combination across multiple sites, your information can be easily used to get into any of these other accounts. Use unique passwords for everything.',
    image: 'https://www.flaticon.com/svg/vstatic/svg/2850/2850551.svg?token=exp=1612195520~hmac=756ec6c037d1e6a879b63ef8a089a42a'
  },
  {
    title: 'Start Using Password Manager',
    description: 'Password managers are services that auto-generate and store strong passwords on your behalf. These passwords are kept in an encrypted, centralized location, which you can access with a master password.',
    image: 'https://www.flaticon.com/svg/vstatic/svg/3050/3050490.svg?token=exp=1612195700~hmac=6e6f52481db798da7c78edeec09aa2ee'
  },
  {
    title: 'Keep Your Password Under Wraps',
    description: 'Don’t give your passwords to anyone else. Don’t type your password into your device if you are within plain sight of other people. And do not plaster your password on a sticky note on your work computer!',
    image: 'https://www.flaticon.com/svg/vstatic/svg/1646/1646125.svg?token=exp=1612195879~hmac=920d37e99cd9ee23dc9f5ab2bbfdbd83'
  },
  {
    title: 'Change Your Password Regularly',
    description: 'The more sensitive your information is, the more often you should change your password. Once it is changed, do not use that password again for a very long time.',
    image: 'https://www.flaticon.com/svg/vstatic/svg/1372/1372789.svg?token=exp=1612195991~hmac=16945141752058634c8987401546f674'
  },
];
const PasswordAdvices = () => {
    return (
        <List
            className={ classes.List }
            itemLayout="horizontal"
            dataSource={data}
            renderItem={item => (
              <List.Item>
                <List.Item.Meta
                  avatar={<Avatar src={item.image} />}
                  title={<p>{item.title}</p>}
                  description={ item.description }
                />
              </List.Item>
            )}
          />
    );
}

export default PasswordAdvices;