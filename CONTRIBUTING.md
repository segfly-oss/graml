## How to Contribute

Open-source thrives because of passionate people.
If you are reading this, thank you for lending your passion for great software!

However, before you get started please observe a few guidelines to ensure quality code and maintain a healthy project:

## Certify Your Work

In order to contribute you must certify you have the rights to the contribution.
All contributions must certify the following:  
(from [developercertificate.org](http://developercertificate.org/))

```
Developer Certificate of Origin
Version 1.1

Copyright (C) 2004, 2006 The Linux Foundation and its contributors.
660 York Street, Suite 102,
San Francisco, CA 94110 USA

Everyone is permitted to copy and distribute verbatim copies of this
license document, but changing it is not allowed.


Developer's Certificate of Origin 1.1

By making a contribution to this project, I certify that:

(a) The contribution was created in whole or in part by me and I
    have the right to submit it under the open source license
    indicated in the file; or

(b) The contribution is based upon previous work that, to the best
    of my knowledge, is covered under an appropriate open source
    license and I have the right under that license to submit that
    work with modifications, whether created in whole or in part
    by me, under the same open source license (unless I am
    permitted to submit under a different license), as indicated
    in the file; or

(c) The contribution was provided directly to me by some other
    person who certified (a), (b) or (c) and I have not modified
    it.

(d) I understand and agree that this project and the contribution
    are public and that a record of the contribution (including all
    personal information I submit with it, including my sign-off) is
    maintained indefinitely and may be redistributed consistent with
    this project or the open source license(s) involved.
```

### Signoff

To indicate you have the authority to certify the contribution, simply sign-off your your commits:

```
git commit --signoff
```

Which appends the following to your commit message:

```
Signed-off-by: Joe Doe <joe.doe@example.com>
```
You must use your real name and email address (or use GitHub's [private email address feature](https://help.github.com/articles/keeping-your-email-address-private/))
Contributions from pseudonyms or anonymous persons will be rejected. 

### GPG Signing Commits

An even better practice is to [GPG Sign](https://ariejan.net/2014/06/04/gpg-sign-your-git-commits/) your commits.
This is completely optional and not a requirement for contributions at this time.

## Pull Requests

Contributions will only be accepted via pull requests.
No patches sent via email or other methods.

### Issue-First Development

Before coding and submitting a pull request, check if there is an open issue for the feature or bug.
If one does not exist, create one and then create your pull request [referencing the issue](https://github.com/blog/1506-closing-issues-via-pull-requests).
Referencing issues from individual commits is discouraged.

### Feature Branching

Pull requests must originate from a feature branch.
This means pull requests will not be accepted from a the master branch of a contributor's fork.
This is generally good practice and prevents polluting the PR with accidental commits on the downstream master branch.

Descriptive feature branch names are highly recommended. E.g. "myfork/oauth-integration-fix" or "myfork/new-chat-dialog"

### Complete Contributions

Pull requests modifying code are likely to be rejected if they are missing any of the following:
* Unit tests
* Integration tests for big contributions
* Reasonable code comments
* Documentation updates (e.g. to README.md) or a wiki entry 

Essentially once the pull request is merged, no further work should be required by the maintainers.
There are exceptions, so this rule is evaluated on a pull-request basis especially if work spans pull requests. 


### Rebase from Upstream

Contributions that contain excessive merges from upstream changes will be rejected.
It is highly recommended that you change your default [pull strategy](http://alblue.bandlem.com/2011/06/git-tip-of-week-pulling-and-rebasing.html) to rebase.
This ensures a clean commit history in the master repo.









