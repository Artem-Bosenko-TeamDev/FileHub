import {Component} from '../components/component.js';
import {Breadcrumbs} from './breadcrumbs.js';
import {UserDetails} from './user-details.js';
import {LogOut} from './log-out.js';
import {FolderControlButtons} from './folder-control-buttons.js';
import {SearchBar} from './search-bar.js';
import {FileList} from './file-list.js';
import {GetRootFolder} from '../services/state-management/get-root-folder-action/get-root-folder.js';
import FetchCurrentFolder from '../services/state-management/fetch-current-directory-action/fetch-current-folder.js';
import {FetchCurrentFolderContent}
  from '../services/state-management/fetch-current-folder-content-action/fetch-current-folder-content.js';

/**
 * Main page for authenticated user, that contains information about him and his saved files.
 */
export class FileListPage extends Component {
  /**
   * @inheritDoc
   * Adds api and title services to page
   * @param {TitleService} titleService
   * @param {StateManager} stateManager
   */
  _init(titleService, stateManager) {
    this._titleService = titleService;
    this._titleService.addTitleForPage('Main Page');
    this._stateManager = stateManager;
  }

  /** @inheritDoc */
  _initNestedComponents() {
    const userPanelElement = this._getElement('user-panel');
    const userDetails = new UserDetails(userPanelElement);
    userDetails.userFullName = 'Oxxxymiron';
    new LogOut(userPanelElement);

    const fileListBodyElement = this._getElement('file-list-body');
    const breadcrumbs = new Breadcrumbs(fileListBodyElement);
    new SearchBar(fileListBodyElement);
    new FolderControlButtons(fileListBodyElement);
    const fileList = new FileList(fileListBodyElement);


    this._stateManager.onStateChanged('currentFolder', (state) => {
      if (state.isCurrentFolderFetching) {
        breadcrumbs.currentDirectory = 'loading';
      }
      breadcrumbs.currentDirectory = state.currentFolder;
    });

    this._stateManager.onStateChanged('locationParams', ({locationParams}) => {
      const currentFolderId = locationParams.currentFolderId;
      if (!currentFolderId) {
        this._stateManager.dispatch(new GetRootFolder());
      } else {
        this._stateManager.dispatch(new FetchCurrentFolder());
        this._stateManager.dispatch(new FetchCurrentFolderContent());
      }
    });

    this._stateManager.onStateChanged('rootFolder', (state) => {
      this._redirect(`index/${state.rootFolder.id}`);
    });

    this._stateManager.onStateChanged('currentFolderContent', (state) => {
      if (state.isCurrentFolderContentFetching) {
        fileList.fileItems = 'loading';
      }
      fileList.fileItems = state.currentFolderContent;
    });
  }

  /**
   * Event to redirect user.
   * @param {function(hash: string)} listener
   */
  onRedirect(listener) {
    this._redirect = listener;
  }

  /** @inheritDoc */
  get _markup() {
    return `<div>
                <header class="header">
                  <h1 title="TeamDev">
                     <a class="logo" href="#index">
                        TeamDev
                     </a>
                  </h1>
                  <ul data-fh="user-panel" class="panel"></ul>
                </header>
                <div data-fh="file-list-body" class="raw page-raw"></div>
                <footer data-fh="footer" class="footer">
                  <ul class="social-icons">
                    <li>
                        <a title="linkedin" class="icon" href="https://www.linkedin.com/company/teamdev-ltd-/mycompany/"
                           target="_blank">
                            <img src="./images/icon-linkedin.png" alt="linkedin">
                        </a>
                    </li>
                    <li>
                        <a title="facebook" class="icon" href="https://www.facebook.com/TeamDev" target="_blank">
                            <img src="./images/icon-facebook.png" alt="facebook">
                        </a>
                    </li>
                    <li>
                        <a title="instagram" class="icon" href="https://www.instagram.com/teamdev_ltd/?hl=ru"
                           target="_blank">
                            <img src="./images/icon-instagram.png" alt="instagram">
                        </a>
                    </li>
                  </ul>
                  <p class="copyright"
                      >Copyright &copy; 2021 <a 
                      title="TeamDev" class="highlight" href="https://www.teamdev.com/" target="_blank">TeamDev</a>. All
                      rights reserved.</p>
                </footer>
            </div>
            `;
  }
}
