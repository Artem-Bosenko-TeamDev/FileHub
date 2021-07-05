import {validationForm} from './validation-form.js';
import {FormGetData} from './components/form-get-data.js';
import {SubmitBox} from './components/submit-box.js';

const form = document.getElementsByClassName('data')[0];

const emailBox = new FormGetData(form);
const passwordBox = new FormGetData(form);
const submitBox = new SubmitBox(form);

emailBox.addLabel('email-user', 'Email');
emailBox.addInput('email-user', 'Email', 'Email');

passwordBox.addLabel('password-user', 'Password');
passwordBox.addInput('password-user', 'Password', 'Password');

submitBox.addLink('Registration', 'Didn\'t have an account yet?', 'registration.html');
submitBox.addButton('Sign In', 'Sign In');

document.getElementById('button').addEventListener('click', () => validationForm(form));
