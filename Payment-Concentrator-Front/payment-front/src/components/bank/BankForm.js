import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import 'bootstrap/dist/css/bootstrap.min.css';

const BankForm = (props) => {
    const columnWidth = 3;
    const style = {
        "marginLeft": "25%",
        "marginTop": "10%",
        "textAlign": "center"
    }
    const styleColumn = {
        "marginBottom": "10px"
    }
    
    return (
        <div>
            <Form style={style} onSubmit={ props.onClick }>
                <Form.Row>
                    <Col xs={columnWidth*2} style={styleColumn}>
                        <Form.Control placeholder="Name on Card" name="name" />
                    </Col>
                </Form.Row>
                <Form.Row>
                    <Col xs={columnWidth*2} style={styleColumn}>
                        <Form.Control placeholder="Card Number" type="password" />
                    </Col>
                </Form.Row>
                <Form.Row>
                    <Col xs={columnWidth} style={styleColumn}>
                        <Form.Control placeholder="MM/YY" />
                    </Col>
                    <Col xs={columnWidth} style={styleColumn}>
                        <Form.Control placeholder="Security Code" type="password" />
                    </Col>
                </Form.Row>
                <Form.Row>
                    <Button 
                        variant="primary" 
                        type="submit" 
                        style={{ "marginLeft": "22%", "marginTop": "10px" }}>
                      Submit
                    </Button>
                </Form.Row>
            </Form>
        </div>
    );
}

export default BankForm;