import { Card, Col, Row, Descriptions, Button } from 'antd';
import Aux from '../../../hoc/Auxiliary';
import classes from './CardEntity.module.css';

const CardEntity = (props) => {

    const mapArrayToString = (array) => {
        let returnString = "";
        array.forEach(element => {
            returnString += element + ", "
        });        
        
        return returnString.substr(0, returnString.length-2);
    }

    const booksDisplay = props.bookChunk.map((bookChunk, index) => {
        const bookCols = bookChunk.map((book, index) => {
            // console.log(book);
            return (
                <Col
                    className = { classes.Column } 
                    span={8} 
                    key={book.id}>
                    <Card 
                        className= { classes.Card }
                        title={book.bookRequest.title} 
                        bordered={false}>
                        <Descriptions column={2} layout="horizontal">
                            <Descriptions.Item 
                                labelStyle={{ fontStyle: "italic" }} 
                                contentStyle = {{ fontWeight: "bold" }}
                                label="Writers">
                                    { mapArrayToString(book.bookRequest.writers) }
                            </Descriptions.Item>
                            <Descriptions.Item 
                                labelStyle={{ fontStyle: "italic" }} 
                                contentStyle = {{ fontWeight: "bold" }}
                                label="Genres">
                                    { mapArrayToString(book.bookRequest.genres) }
                            </Descriptions.Item>
                            <Descriptions.Item 
                                labelStyle={{ fontStyle: "italic" }} 
                                contentStyle = {{ fontWeight: "bold" }}
                                label="Synopsis"
                                span={2}>
                                    { book.bookRequest.synopsis }
                            </Descriptions.Item>
                            <Descriptions.Item 
                                labelStyle={{ fontStyle: "italic" }} 
                                contentStyle = {{ fontWeight: "bold" }}
                                label="Publish Place"
                                span={2}>
                                    { book.publishPlace }
                            </Descriptions.Item>
                            <Descriptions.Item 
                                labelStyle={{ fontStyle: "italic" }} 
                                contentStyle = {{ fontWeight: "bold" }}
                                label="Publish Year"
                                span={2}>
                                    { book.publishYear }
                            </Descriptions.Item>
                            <Descriptions.Item 
                                labelStyle={{ fontStyle: "italic" }} 
                                contentStyle = {{ fontWeight: "bold" }}
                                label="Page no.">
                                    { book.numberOfPages }
                            </Descriptions.Item>
                            <Descriptions.Item 
                                labelStyle={{ fontStyle: "italic" }} 
                                contentStyle = {{ fontWeight: "bold", color: "darkgreen" }}
                                label="Price">
                                    { book.price } €
                            </Descriptions.Item>
                            <Descriptions.Item span={2}></Descriptions.Item>
                            <Descriptions.Item></Descriptions.Item>
                            { props.isAddToCartVisible ? 
                            <Descriptions.Item style={{ float:"right" }} span={2}>
                                <Button
                                    onClick={ () => props.onaddToCart(book) } 
                                    type="primary" ghost>
                                  Add To Cart
                                </Button>
                            </Descriptions.Item> : null }
                        </Descriptions>
                    </Card>
                </Col>
              );
            });
            return <Row gutter={16} key={index}>{bookCols}</Row>
    });

    return (
        <Aux>
            { booksDisplay }
        </Aux>
    );
}

export default CardEntity;