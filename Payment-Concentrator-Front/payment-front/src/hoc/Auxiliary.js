import './Auxiliary.css';

const aux = (props) => {
    return (
        <div className={ props.classes }>
            { props.children }
        </div>
    );
}

export default aux;