import {Component} from '../components/component.js';
import {Form} from '../components/form.js';
import {FormInputField} from '../components/form-input-field.js';
import {Validator} from '../validation/validator.js';
import {ParameterConfiguration, ValidationConfiguration} from '../validation/validation-configuration.js';
import {lengthValidation, emailRegexpValidation} from '../validation/validation-rules.js';
import {UserData} from '../user-data.js';

/**
 * Component for authentication page that allows to get and validate user email and password.
 */
export class AuthenticationForm extends Component {
  /**
   * Adds some event, which will be called on submitting form.
   * @param {function} event
   */
  onSubmit(event) {
    this._onSubmitAuthenticationEvent = event;
  }

  /**
   * Adds server's error message to authentication form.
   * @param {string} errorMessage
   */
  addServerError(errorMessage) {
    alert(errorMessage);
  }

  /**
   * Adds error messages to inputs after analyzes validation results.
   * @param {PromiseRejectedResult[]} resultsOfValidation
   * @private
   * @returns {void}
   */
  _renderErrorMessages(resultsOfValidation) {
    resultsOfValidation
        .filter((result) => result.status === 'rejected')
        .forEach((result) => result.reason.component.errorMessage = result.reason.message);
  }

  /**
   * @inheritDoc
   */
  _initNestedComponents() {
    this._form = new Form(this.rootElement);
    this._form.formHeader = 'Sign In to FileHub';
    this._form.buttonTitle = 'Sign In';
    this._form.linkMessage = 'Didn\'t have an account yet?';
    this._form.linkReference = '#register';

    this._form.initInputs((container) => {
      this._emailInputField = new FormInputField(container);
      this._passwordInputField = new FormInputField(container);

      this._emailInputField.id = 'email-user';
      this._emailInputField.title = 'Email';
      this._emailInputField.inputType = 'text';
      this._emailInputField.onChange((value)=> this._emailInputField.errorMessage = value);

      this._passwordInputField.id = 'password-user';
      this._passwordInputField.title = 'Password';
      this._passwordInputField.inputType = 'password';
      this._passwordInputField.onChange((value)=> this._passwordInputValue = value);
    });

    this._form.onSubmit = async () => {
      this._emailInputField.cleanErrorMessage();
      this._passwordInputField.cleanErrorMessage();

      this._emailInputValue = this._emailInputField.inputValue;
      this._passwordInputValue = this._passwordInputField.inputValue;

      const authenticationFormValidator = new Validator(
          new ValidationConfiguration(
              [
                new ParameterConfiguration(lengthValidation(this._emailInputField, this._emailInputValue, 5)),
                new ParameterConfiguration(emailRegexpValidation(this._emailInputField, this._emailInputValue)),
                new ParameterConfiguration(
                    lengthValidation(this._passwordInputField, this._passwordInputValue, 6)),
              ],
          ),
      );
      const results = await authenticationFormValidator.validate();
      const isAnyPromiseStatusReject = results.some((result) => result.status === 'rejected');
      if (isAnyPromiseStatusReject) {
        this._renderErrorMessages(results);
      } else {
        this._onSubmitAuthenticationEvent && this._onSubmitAuthenticationEvent(
            new UserData(this._emailInputField, this._passwordInputValue));
      }
    };
  }

  /**
   * @inheritDoc
   */
  get _markup() {
    return `<div class="raw"></div>`;
  }
}
