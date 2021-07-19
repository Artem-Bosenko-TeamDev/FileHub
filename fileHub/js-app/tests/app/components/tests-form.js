import {Form} from '../../../app/components/form.js';
import {FormInputField} from '../../../app/components/form-input-field.js';
import {searchElement} from '../search-element-function.js';

const {module, test} = QUnit;
let fixture;

module('Form', {
  beforeEach: () => {
    fixture = document.getElementById('qunit-fixture');
  },
});

test('Should create form with header, link reference and message, buttonTitle', (assert) => {
  const header = 'header';
  const linkRef = 'link';
  const linkMessage = 'this is link message';

  assert.expect(3);
  const form = new Form(fixture);
  form.formHeader = header;
  assert.ok(searchElement('header'), 'Should create header in form');

  form.linkReference = linkRef;
  assert.ok(document.querySelector(`[data-fh="link"][href="${linkRef}"]`),
      'Should add reference to link in form');

  form.linkMessage = linkMessage;
  assert.equal(searchElement('link').innerHTML, linkMessage, 'Should create header in form');
});

test('Should create form with input field', (assert) => {
  const form = new Form(fixture);
  form.initInputs((container) => {
    const inputField = new FormInputField(container);
    inputField.id = 'id';
  });

  assert.ok(searchElement('inputid'), 'Should create input field in form');
});

test('Should check event calls in form ', (assert) => {
  const step = 'step';
  const form = new Form(fixture);
  form.onSubmit = () => assert.step(step);
  searchElement('form').dispatchEvent(new Event('submit'));
  assert.verifySteps([step], 'Should successfully calls event on submit form');
});
