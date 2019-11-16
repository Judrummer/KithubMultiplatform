import UIKit
import core

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    
    let viewModel = CoreModule().provideMainViewModel()
    
    let subscriptons = KCompositeSubscriptions()
    
    internal func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        viewModel.rootNavigationEvent.subscribe(subscriptions: subscriptons) {[weak self] rootNavigationEvent in
            guard let self = self  else { return }
            
            let storyBoard = UIStoryboard(name: "Main", bundle: nil)
            switch rootNavigationEvent {
            case is MainRootNavigationEvent.Login :
                let loginVC = storyBoard.instantiateViewController(withIdentifier: "LoginViewController") as! LoginViewController
                self.window?.rootViewController = loginVC
            case is MainRootNavigationEvent.RepoList :
                let repoNC = storyBoard.instantiateViewController(withIdentifier: "RepoNavigationController")
                as! RepoNavigationController
                self.window?.rootViewController = repoNC
            default:
                break
            }
        }
        viewModel.initialize()
        return true
    }
    
    func applicationWillResignActive(_ application: UIApplication) {}
    
    func applicationDidEnterBackground(_ application: UIApplication) {}
    
    func applicationWillEnterForeground(_ application: UIApplication) {}
    
    func applicationDidBecomeActive(_ application: UIApplication) {}
    
    func applicationWillTerminate(_ application: UIApplication) {
        subscriptons.clear()
        viewModel.dispose()
    }
}
