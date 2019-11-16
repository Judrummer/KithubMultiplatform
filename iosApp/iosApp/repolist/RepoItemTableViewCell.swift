import UIKit
import core

class RepoItemTableViewCell: UITableViewCell {

    @IBOutlet weak var repoNameLabel: UILabel!
    @IBOutlet weak var repoDescriptionLabel: UILabel!
    @IBOutlet weak var repoStarLabel: UILabel!
    
    func bind(repoItem: RepoItem) {
        repoNameLabel.text = repoItem.name
        repoDescriptionLabel.text = repoItem.descriptionText
        repoStarLabel.text = "\(repoItem.starCount)"
    }
    
}
