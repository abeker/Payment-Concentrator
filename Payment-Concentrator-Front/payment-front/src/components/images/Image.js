import PropTypes from 'prop-types';
import bitcoin from '../../assets/images/bitcoin.svg';
import paypal from '../../assets/images/paypal.svg';
import newLU from '../../assets/images/newLU.svg';
import raiffeisenImage from '../../assets/images/raiffeisen.png';
import unicreditImage from '../../assets/images/unicredit1.png';
import bank from '../../assets/images/bank.svg';
import classes from './image.module.css';

const image = (props) => {
    let img = null;
    if(props.type === 'BITCOIN') {
        img = <img src={ bitcoin } className={ classes.Image } alt='BITCOIN' onClick={ props.clicked } />;
    } else if(props.type === 'RAIFFEISEN') {
        img = <img src={ raiffeisenImage } alt='RAIFFEISEN' className={ classes.Image } onClick={ props.clicked } />;
    } else if(props.type === 'VISA') {
        img = <img src={ bank } alt='VISA' className={ classes.Image } onClick={ props.clicked } />;
    } else if(props.type === 'UNICREDIT') {
        img = <img src={ unicreditImage } alt='UNICREDIT' className={ classes.Image } onClick={ props.clicked } />;
    } else if(props.type === 'PAYPAL') {
        img = <img src={ paypal } alt='PAYPAL' className={ classes.Image } onClick={ props.clicked } />;
    } else if(props.type === 'NEW_LU') {
        img = <img src={ newLU } 
            alt='NEW_LU' 
            className={ classes.Image }
            style={{ width: '140px' }} 
            onClick={ props.clicked } />;
    } else if(props.type === 'LU_PAYPAL') {
        img = <img src={ paypal } alt='LU_PAYPAL'  className={ classes.ImageLU } onClick={ props.clicked } />;
    } else if(props.type === 'LU_BITCOIN') {
        img = <img src={ bitcoin } alt='LU_BITCOIN'  className={ classes.ImageLU } onClick={ props.clicked } />;
    } else if(props.type === 'LU_BANK') {
        img = <img src={ bank } alt='LU_BANK'  className={ classes.ImageLU } onClick={ props.clicked } />;
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