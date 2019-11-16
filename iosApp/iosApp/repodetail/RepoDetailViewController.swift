import UIKit
import core

class RepoDetailViewController: UIViewController {
    
    @IBOutlet weak var contentView: UIView!
    @IBOutlet weak var repoNameLabel: UILabel!
    @IBOutlet weak var repoDescriptionLabel: UILabel!
    @IBOutlet weak var repoSshUrlLabel: UILabel!
    @IBOutlet weak var repoStarLabel: UILabel!
    @IBOutlet weak var repoLanguageLabel: UILabel!
    @IBOutlet weak var repoContributorListTableView: UITableView!
    
    
    var repoName:String = ""
    
    let viewModel = CoreModule().provideRepoDetailViewModel()
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
        contentView.alpha = 0
        repoContributorListTableView.dataSource = self
        repoContributorListTableView.register(UINib(nibName: "ContributorItemTableViewCell", bundle: nil), forCellReuseIdentifier: "ContributorItemTableViewCell")
        
        repoLanguageLabel.layer.masksToBounds = true
        repoLanguageLabel.layer.cornerRadius = 8
        viewModel.state.subscribe(subscriptions: subscriptions){[weak self] state in
            guard let self = self else { return }
            if state.loading {
                self.activityIndicator.startAnimating()
            } else {
                self.activityIndicator.stopAnimating()
                self.contentView.alpha = 1
            }
            
            self.repoNameLabel.text = state.name
            self.repoDescriptionLabel.text = state.descriptionText
            self.repoStarLabel.text = "\(state.starCount)"
            self.repoSshUrlLabel.text = state.sshUrl
            self.repoLanguageLabel.text = "  \(state.language)  "
            
            self.repoContributorListTableView.reloadData()
        }
        
        viewModel.error.subscribe(subscriptions: subscriptions) { [weak self] error in
            guard let self = self else { return }
            let alertController = UIAlertController(title: "Error", message: error.message, preferredStyle: .alert)
            let okAction = UIAlertAction(title: "OK", style: .default, handler: nil)
            alertController.addAction(okAction)
            self.present(alertController, animated: true, completion: nil)
        }
        
        viewModel.refresh(repoName: repoName)
    }
}

extension RepoDetailViewController: UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.state.currentValue.contributors.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ContributorItemTableViewCell", for: indexPath) as! ContributorItemTableViewCell
        cell.selectionStyle = .none
        let contributorItem = viewModel.state.currentValue.contributors[indexPath.row]
        cell.bind(contributorItem: contributorItem)
        return cell
    }
}
