import UIKit
import core
import Kingfisher
class ContributorItemTableViewCell: UITableViewCell {
    
    @IBOutlet weak var contributorAvatarImageView: UIImageView!
    @IBOutlet weak var contributorNameLabel: UILabel!
    @IBOutlet weak var contributionLabel: UILabel!
    func bind(contributorItem:ContributorItem){
        contributorNameLabel.text = contributorItem.name
        contributionLabel.text = "\(contributorItem.contributions)"
        contributorAvatarImageView.layer.masksToBounds = true
        contributorAvatarImageView.layer.cornerRadius = 15
        contributorAvatarImageView.kf.setImage(with: URL(string: contributorItem.avatarUrl))
    }
    
}
