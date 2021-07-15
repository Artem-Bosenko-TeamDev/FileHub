import {AuthenticationPage} from '../../../app/login/authentication-page.js';
import {TitleService} from '../../../app/services/title-service.js';

const {module, test} = QUnit;

module('Authentication page', (hooks) => {
  let fixture;
  hooks.beforeEach(() => {
    fixture = document.getElementById('qunit-fixture');
  });
  const testDocument = {
    title: '',
  };

  test('Should add title and api services to authentication page', (assert) => {
    assert.expect(2);
    const done = assert.async();
    const testApiService = {
      logIn(email, password) {
        assert.ok(true, 'Should be called event on submit.');
        done();
      },
    };
    const titleService = new TitleService('FileHub', testDocument);
    const page = new AuthenticationPage(fixture, testApiService, titleService);

    page.getElement('inputemail-user').value = 'emailvvvdvdv';
    page.getElement('inputpassword-user').value = '123654987';
    page.getElement('form').dispatchEvent(new Event('submit'));

    assert.equal(testDocument.title, 'Authentication - FileHub', 'Should add title to page');
  });
});
