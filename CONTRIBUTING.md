# Contributing to healthBAM

Quick Links:

* [Creating Issues](#issues)
* [Committing Code](#committing)
* [Submitting Pull Requests](#pull-requests)


## <a name="issues"></a> Creating Issues
All changes to the healthBAM codebase must be done against an Issue in our GitHub.
[Create a new Issue](https://github.com/healthbam/healthbam/issues/new) to report a bug or request a feature
before committing code. It is also recommended to search for an existing issue first as it may already have been reported.
Issues can be viewed and managed at our [waffle.io board](https://waffle.io/healthbam/healthbam).

## <a name="committing"></a> Committing Code
All work must be done against an existing [Issue](https://github.com/healthbam/healthbam/issues/new).
The commit message must have the issue number (with leading "#") in the first line.
The following lines in the commit message should explain what was changed and why.

Example:


        Issue #99: Collaboration Rules Needed
        
        Creates CONTRIBUTING.md to explain the rules and workflow for code changes.
        Adds a "Creating Issues" section with a link and instructions.
        Adds a "Committing Code" section that explains the Issue requirement and commit messages rules.
        Adds a "Submitting Pull Requests" section to explain the git workflow, forks, and pull-requests.
        
        Adds a Contributing section to README.md that links to the new CONTRIBUTING.md.
        


## <a name="pull-requests"></a> Submitting Pull Requests
There are many possible workflows for introducing changes to the healthBAM repository. Learn git!

A possible work flow is as follows:

1. Create your own fork of the [healthbam/healthbam repository](https://github.com/healthbam/healthbam),
   by clicking the "Fork" button.
2. Clone your new fork of the healthbam repository to your workstation.
   This will map the remote "origin" to your fork:


        git clone git@github.com:YOUR_USERNAME/healthbam.git


3. Add the healthbam/healthbam repository as the remote "upstream":


        git remote add upstream git@github.com:healthbam/healthbam.git


4. Fetch all changes from both remotes.


        git fetch --all


5. Create and checkout a branch for the issue of work:


        git checkout -b Issue-99-Contributing upstream/master


6. Implement and commit work. Include unit tests that thoroughly test your changes.
7. Fetch all changes from server again (step 4) and then rebase your work to ensure no conflicts have been introduced:


        git fetch --all
        git rebase upstream/master


8. Push your branch to your fork.
   If this is the first time you have done so or there were no changes on which to rebase,
   you do not need the `--force` flag.


        git push -u origin HEAD --force

9. In GitHub, within your fork, review your work and make any changes required by repeating steps 6-8.
10. Submit a pull-request to healthbam/healthbam.
11. When reviewers have reviewed the work and no changes are required, it can be merged into the codebase.
