import { Card, Col, Row, Descriptions } from 'antd';
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
                        onClick={ () => props.onclickToCard(book) }
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
                                label="Publish Place">
                                    { book.publishPlace }
                            </Descriptions.Item>
                            <Descriptions.Item 
                                labelStyle={{ fontStyle: "italic" }} 
                                contentStyle = {{ fontWeight: "bold" }}
                                label="Publish Year">
                                    { book.publishYear }
                            </Descriptions.Item>
                            <Descriptions.Item 
                                labelStyle={{ fontStyle: "italic" }} 
                                contentStyle = {{ fontWeight: "bold" }}
                                label="Number of Pages">
                                    { book.numberOfPages }
                            </Descriptions.Item>
                            <Descriptions.Item 
                                labelStyle={{ fontStyle: "italic" }} 
                                contentStyle = {{ fontWeight: "bold", color: "darkgreen" }}
                                label="Price">
                                    { book.price } rsd
                            </Descriptions.Item>
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