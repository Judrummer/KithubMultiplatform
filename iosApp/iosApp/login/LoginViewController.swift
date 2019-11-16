import UIKit
import core

class LoginViewController: UIViewController {
    
    @IBOutlet weak var usernameTextField: UITextField!
    @IBOutlet weak var loginButton: UIButton!
    
    let viewModel = CoreModule().provideLoginViewModel()
    let subscriptions = KCompositeSubscriptions()
    let activityIndicator = UIActivityIndicatorView.init(style: .whiteLarge)
    let loadingView = UIView()
    
    deinit {
        subscriptions.clear()
        viewModel.dispose()
    }
    
    @IBAction func onLoginButtonClick(_ sender: Any) {
        viewModel.login()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLoadingView()
        bindViewModel()
        usernameTextField.addTarget(self, action: #selector(onUsernameChanged(_:)), for: .editingChanged)
    }
    
    @objc func onUsernameChanged(_ textField: UITextField){
        viewModel.setUsername(username: textField.text ?? "")
    }
    
    private func bindViewModel() {
        viewModel.state.subscribe(subscriptions: subscriptions){[weak self] state in
            guard let self = self else { return }
            self.loginButton.isEnabled = state.isValid
            self.loginButton.backgroundColor = state.isValid ? UIColor.blue : UIColor.lightGray
            
            if(state.loading) {
                self.activityIndicator.startAnimating()
                self.loadingView.alpha = 0.5
            } else {
                self.activityIndicator.stopAnimating()
                self.loadingView.alpha = 0
            }
        }
        
        viewModel.error.subscribe(subscriptions: subscriptions) { [weak self] error in
            guard let self = self else { return }
            let alertController = UIAlertController(title: "Error", message: error.message, preferredStyle: .alert)
            let okAction = UIAlertAction(title: "OK", style: .default, handler: nil)
            alertController.addAction(okAction)
            self.present(alertController, animated: true, completion: nil)
        }
        
        viewModel.loginCompleteEvent.subscribe(subscriptions: subscriptions) { [weak self] _ in
            guard let self = self else { return }
            let repoNC = self.storyboard!.instantiateViewController(withIdentifier: "RepoNavigationController")
                as! RepoNavigationController
            repoNC.modalPresentationStyle = .fullScreen
            self.present(repoNC, animated: true, completion: nil)
            
        }
    }
    
    private func setupLoadingView() {
        loadingView.frame = view.frame
        loadingView.backgroundColor = .black
        loadingView.alpha = 0
        loadingView.addSubview(activityIndicator)
        activityIndicator.center = loadingView.center
        view.addSubview(loadingView)
    }
}
