import { List, Avatar } from 'antd';
import classes from './PasswordAdvices.module.css';
import firstAdvice from '../../assets/images/meter.svg';
import secondAdvice from '../../assets/images/scribble.svg';
import thirdAdvice from '../../assets/images/randomNumber.svg';
import fourthAdvice from '../../assets/images/personalInfo.svg';
import fifthAdvice from '../../assets/images/reuse.svg';
import sixthAdvice from '../../assets/images/passwordManager.svg';
import seventhAdvice from '../../assets/images/protec.svg';
import eighthAdvice from '../../assets/images/change.svg';

const data = [
  {
    title: 'Make Your Password Long',
    description: 'The longer and more complex your password is, the longer is process of hacking. Recommended minimum password length is 7 characters.',
    image: firstAdvice
  },
  {
    title: 'Make Your Password Nonsense Phrase',
    description: 'Long passwords are good. Long passwords that include random words and phrases are better. Do not use dictionary words. A common phrase, "outofthepark," is only marginally more secure than a dictionary word. Also do not use characters that are sequential on a keyboard such as numbers in order or the widely used “qwerty”.',
    image: secondAdvice
  },
  {
    title: 'Include Numbers, Symbols, Uppercase & Lowercase Letters',
    description: 'Randomly mix up symbols and numbers with letters. If your password is a phrase, consider capitalizing the first letter of each new word, which will be easier for you to remember.',
    image: thirdAdvice
  },
  {
    title: 'Avoid Using Obvious Personal Information',
    description: 'If there is information about you that is easily discoverable - such as your birthday, address, city of birth, high school, pets’ names - do not include them in your password. These only make your password easier to guess.',
    image: fourthAdvice
  },
  {
    title: 'Do Not Reuse Passwords',
    description: 'If your account is compromised and you use this email address and password combination across multiple sites, your information can be easily used to get into any of these other accounts. Use unique passwords for everything.',
    image: fifthAdvice
  },
  {
    title: 'Start Using Password Manager',
    description: 'Password managers are services that auto-generate and store strong passwords on your behalf. These passwords are kept in an encrypted, centralized location, which you can access with a master password.',
    image: sixthAdvice
  },
  {
    title: 'Keep Your Password Under Wraps',
    description: 'Don’t give your passwords to anyone else. Don’t type your password into your device if you are within plain sight of other people. And do not plaster your password on a sticky note on your work computer!',
    image: seventhAdvice
  },
  {
    title: 'Change Your Password Regularly',
    description: 'The more sensitive your information is, the more often you should change your password. Once it is changed, do not use that password again for a very long time.',
    image: eighthAdvice
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