import React from 'react';
import { Card } from 'react-bootstrap';

const Class = () => {
  return (
    <Card border="info" style={{ width: '10rem', height: '10rem' }}>
      <Card.Header>Ms.Ellie</Card.Header>
      <Card.Body>
        <Card.Subtitle>Reformer, Chair</Card.Subtitle>
        <footer className="blockquote-footer">18:00</footer>
      </Card.Body>
    </Card>
  );
};

export default Class;
