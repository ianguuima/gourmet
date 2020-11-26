import React from 'react';
import './App.css';
import { Nav } from './components/Nav/Nav';
import QuestionResolver from './components/resolver/QuestionResolver';

function App() {
  return (
    <div className="App">
      <header className="App-header">

        <Nav>
          <h1>Aladish</h1>
          <p>the food guesser!</p>
        </Nav>

        <QuestionResolver/>

      </header>
    </div>
  );
}

export default App;
