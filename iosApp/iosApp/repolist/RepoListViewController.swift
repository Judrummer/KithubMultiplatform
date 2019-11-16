import UIKit
import core
import Kingfisher
class RepoListViewController: UIViewController {
    
    @IBOutlet weak var contentView: UIView!
    @IBOutlet weak var displayNameLabel: UILabel!
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var avatarImageVIew: UIImageView!
    
    @IBOutlet weak var repoListTabelView: UITableView!
    
    @IBAction func onLogoutButtonClick(_ sender: Any) {
        viewModel.logout()
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        let loginVC = self.storyboard!.instantiateViewController(withIdentifier: "LoginViewController") as! LoginViewController
        appDelegate.window?.rootViewController = loginVC
    }
    
    let viewModel = CoreModule().provideRepoListViewModel()
    let subscriptions = KCompositeSubscriptions()
    let activityIndicator = UIActivityIndicatorView.init(style: .gray)
    
    deinit {
        subscriptions.clear()
        viewModel.dispose()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        activityIndicator.center = view.center
        view.addSubview(activityIndicator)
        repoListTabelView.delegate = self
        repoListTabelView.dataSource = self
        repoListTabelView.register(UINib(nibName: "RepoItemTableViewCell", bundle: nil), forCellReuseIdentifier: "RepoItemTableViewCell")
        contentView.alpha = 0
        
        viewModel.state.subscribe(subscriptions: subscriptions){[weak self] state in
            guard let self = self else { return }
            if state.loading {
                self.activityIndicator.startAnimating()
            } else {
                self.activityIndicator.stopAnimating()
                self.contentView.alpha = 1
            }
            
            self.displayNameLabel.text = state.displayName
            self.usernameLabel.text = state.username
            self.avatarImageVIew.kf.setImage(with:URL(string: state.avatarUrl))
            self.repoListTabelView.reloadData()
        }
        
        viewModel.error.subscribe(subscriptions: subscriptions) { [weak self] error in
            guard let self = self else { return }
            let alertController = UIAlertController(title: "Error", message: error.message, preferredStyle: .alert)
            let okAction = UIAlertAction(title: "OK", style: .default, handler: nil)
            alertController.addAction(okAction)
            self.present(alertController, animated: true, completion: nil)
        }
        viewModel.refresh()
    }
}


extension RepoListViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let repoItem = viewModel.state.currentValue.repos[indexPath.row]
        let repoDetailVC = self.storyboard!.instantiateViewController(withIdentifier:  "RepoDetailViewController") as! RepoDetailViewController
        repoDetailVC.modalPresentationStyle = .fullScreen
        repoDetailVC.repoName = repoItem.name
        self.navigationController?.pushViewController(repoDetailVC, animated: true)
        
    }
}

extension RepoListViewController: UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.state.currentValue.repos.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "RepoItemTableViewCell", for: indexPath) as! RepoItemTableViewCell
        cell.selectionStyle = .none
        let repoItem = viewModel.state.currentValue.repos[indexPath.row]
        cell.bind(repoItem: repoItem)
        return cell
    }
}
