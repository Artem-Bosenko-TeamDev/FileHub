import {Button} from '../../../app/components/button.js';
import {searchElement} from '../search-element-function.js';

const {module, test} = QUnit;
let button;

module('Button', {
  beforeEach: () => {
    const fixture = document.getElementById('qunit-fixture');
    button = new Button(fixture);
  },
});

test('Should create button', (assert) => {
  assert.ok(searchElement('button'),
      'Should add 1 button with class button');
});

test('Should create button with title', (assert) => {
  const title = 'title';
  button.buttonTitle = title;

  assert.equal(searchElement('button').innerHTML, title,
      'Should add inner text to button: ' + title);
});

/*
test('Should adding event listener on button click', (assert) => {
  const step = 'This step on event button';
  assert.expect(2);
  button.onClick(() => assert.step(step));
  searchElement('button').dispatchEvent(new Event('click'));
  assert.verifySteps([step]);
});
*/
