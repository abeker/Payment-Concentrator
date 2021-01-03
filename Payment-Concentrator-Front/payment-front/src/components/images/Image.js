import bitcoin from '../../assets/images/bitcoin.jpg';
import visa from '../../assets/images/visa.jpg';
import raiffeisenImage from '../../assets/images/raiffeisen.png';
import unicreditImage from '../../assets/images/unicredit1.png';
import paypal from '../../assets/images/paypal.png';
import PropTypes from 'prop-types';
import Aux from '../../hoc/Auxiliary';
import classes from './image.module.css';

const image = (props) => {
    let img = null;
    if(props.type === 'BITCOIN') {
        img = <img src={ bitcoin } className={ classes.Image } alt='BITCOIN' onClick={ props.clicked } />;
    } else if(props.type === 'RAIFFEISEN') {
        img = <img src={ raiffeisenImage } alt='RAIFFEISEN' className={ classes.Image } onClick={ props.clicked } />;
    } else if(props.type === 'VISA') {
        img = <img src={ visa } alt='VISA' className={ classes.Image } onClick={ props.clicked } />;
    } else if(props.type === 'UNICREDIT') {
        img = <img src={ unicreditImage } alt='UNICREDIT' className={ classes.Image } onClick={ props.clicked } />;
    } else if(props.type === 'PAYPAL') {
        img = <img src={ paypal } alt='PAYPAL' className={ classes.Image } onClick={ props.clicked } />;
    }
    
    return (
        <span>
            { img }
        </span>
    );
};

image.propTypes = {
    type: PropTypes.string.isRequired,
    clicked: PropTypes.func
}

export default image;